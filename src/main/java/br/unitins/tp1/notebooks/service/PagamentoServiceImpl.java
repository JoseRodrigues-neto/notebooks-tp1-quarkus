package br.unitins.tp1.notebooks.service;

 
import br.unitins.tp1.notebooks.repository.PedidoRepository;
import br.unitins.tp1.notebooks.repository.PixRepository;
import br.unitins.tp1.notebooks.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
 

import br.unitins.tp1.notebooks.dto.CartaoDTO;
import br.unitins.tp1.notebooks.modelo.FormaPagamento;
import br.unitins.tp1.notebooks.modelo.PagamentoBoleto;
import br.unitins.tp1.notebooks.repository.BoletoRepository;
import br.unitins.tp1.notebooks.repository.CartaoRepository;
 
import br.unitins.tp1.notebooks.modelo.PagamentoCartao;
import br.unitins.tp1.notebooks.modelo.PagamentoPix;
import br.unitins.tp1.notebooks.modelo.Pedido;
import br.unitins.tp1.notebooks.modelo.StatusPedido;
 

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    CartaoRepository cartaoRepository;
    
    @Inject
    PixRepository pixRepository; 
     // Supondo que você tenha um repositório para Pix
    @Inject
    BoletoRepository boletoRepository; 

 
    @Override
    @Transactional
    public FormaPagamento cadastraCartao(CartaoDTO pagamentoCartaoDTO) {
        
        // Cria uma nova instância de PagamentoCartao com base no DTO recebido
        PagamentoCartao pagamentoCartao = new PagamentoCartao(
       
            pagamentoCartaoDTO.tipoCartao(),
            pagamentoCartaoDTO.numeroCartao(),
            pagamentoCartaoDTO.nomeTitular(),
            pagamentoCartaoDTO.validade(),
            pagamentoCartaoDTO.codigoSeguranca()
        );
        
        // Salva a entidade no banco de dados
        cartaoRepository.persist(pagamentoCartao);
        
        // Retorna a entidade salva como uma forma de pagamento
        return pagamentoCartao;
    }

    @Override
    @Transactional
    public void pagamentoCartao(Long pedidoId) {
        // Buscar o pedido pelo ID
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new ValidationException("Cliente", "cliente invalido");
        }
    
        if ("Pedido pago".equals(pedido.getStatus().getDescricao())) {
            throw new ValidationException("Status:", "Pedido já foi pago.");
        }
    
        pedido.setStatus(StatusPedido.PAGO);  
        pedido.setTipoPagamento("Cartão");
        pedidoRepository.persist(pedido);
    
      
    }
    

    @Override
    @Transactional
    public void pagamentoPix(Long pedidoId) {

      //precisa pegar a autentificação
        // Buscar o pedido pelo ID
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoId);
        }
    
        // Verificar se o pedido já está pago (evitar reprocessamento)
        if ("PAGO".equals(pedido.getStatus())) {
            throw new ValidationException("Status", "Pedido já foi pago.");
        }
        
        // boolean temCartaoCadastrado = cartaoRepository.existsByClienteId(clienteId);
        // if (!temCartaoCadastrado) {
        //     throw new ValidationException("Cartão", "Cliente não possui cartão cadastrado.");
        // }
        
        // Atualizar o status do pedido para "Pago"
        pedido.setStatus(StatusPedido.PAGO);  
        pedido.setTipoPagamento("PIX"); // Definir tipo de pagamento como PIX
        pedidoRepository.persist(pedido);  // Persistindo o pedido com o novo status
    
        // Caso necessário, adicione informações como código de transação aqui, se houver
        // Exemplo:
        // pedido.setCodigoTransacaoPix(codigoTransacao);
        // pedidoRepository.persist(pedido);
    }


@Override
@Transactional
public void pagamentoBoleto(Long pedidoId) {
    // Buscar o pedido pelo ID
    Pedido pedido = pedidoRepository.findById(pedidoId);
    if (pedido == null) {
        throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoId);
    }

    // Verificar se o pedido já está pago (evitar reprocessamento)
    if ("Pedido pago".equals(pedido.getStatus())) {
        throw new ValidationException("Status", "Pedido já foi pago.");
    }

    // Caso tenha alguma lógica para validar o pagamento via Boleto (como verificar o pagamento),
    // adicione essa validação aqui. Por enquanto, vamos simular que o pagamento foi concluído.
    
    // Atualizar o status do pedido para "Pago"
    pedido.setStatus(StatusPedido.PAGO);  
    pedido.setTipoPagamento("Boleto"); // Definir tipo de pagamento como Boleto
    pedidoRepository.persist(pedido);  // Persistindo o pedido com o novo status

    // Caso necessário, adicione informações como código de compensação do boleto aqui, se houver
    // Exemplo:
    // pedido.setCodigoBoleto(codigoBoleto);
    // pedidoRepository.persist(pedido);
}
    
}
