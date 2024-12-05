package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.repository.PedidoRepository;
import br.unitins.tp1.notebooks.repository.PixRepository;
import br.unitins.tp1.notebooks.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.eclipse.microprofile.jwt.JsonWebToken;

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
 
    @Inject
    BoletoRepository boletoRepository;

    @Inject
    JsonWebToken jwt;

    @Override
    @Transactional
    public FormaPagamento cadastraCartao(CartaoDTO pagamentoCartaoDTO) {

        PagamentoCartao pagamentoCartao = new PagamentoCartao(

                pagamentoCartaoDTO.tipoCartao(),
                pagamentoCartaoDTO.numeroCartao(),
                pagamentoCartaoDTO.nomeTitular(),
                pagamentoCartaoDTO.validade(),
                pagamentoCartaoDTO.codigoSeguranca());

        cartaoRepository.persist(pagamentoCartao);
        return pagamentoCartao;
    }

    @Override
    @Transactional
    public void pagamentoCartao(Long pedidoId) {

        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new ValidationException("Pedido", "Pedido inválido.");
        }
        String usernameAutenticado = jwt.getName();

        if (!pedido.getCliente().getUsuario().getUsername().equals(usernameAutenticado)) {
            throw new ValidationException("Pedido", "Pedido inválido.");
        }

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new ValidationException("Status", "Não é possível pagar um pedido cancelado.");
        }

        if ("Pedido pago".equals(pedido.getStatus().getDescricao())) {
            throw new ValidationException("Status", "Pedido já foi pago.");
        }

        boolean temCartaoCadastrado = cartaoRepository.findByClienteId(pedido.getCliente().getId());
        if (!temCartaoCadastrado) {
            throw new ValidationException("Cartão", "Você não possui um cartão cadastrado.");
        }

        pedido.setStatus(StatusPedido.PAGO);
        pedido.setTipoPagamento("Cartão");
        pedidoRepository.persist(pedido);
    }

    @Override
    @Transactional
    public void pagamentoPix(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new ValidationException("ID", "Pedido não encontrado com o ID: " + pedidoId);
        }

        String usernameAutenticado = jwt.getName();

        if (!pedido.getCliente().getUsuario().getUsername().equals(usernameAutenticado)) {
            throw new ValidationException("PedidoID", "Pedido inválido.");
        }

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new ValidationException("Status", "Não é possível pagar um pedido cancelado.");
        }

        if ("Pedido pago".equals(pedido.getStatus().getDescricao())) {
            throw new ValidationException("Status", "Pedido já foi pago.");
        }

        pedido.setStatus(StatusPedido.PAGO);
        pedido.setTipoPagamento("PIX");
        pedidoRepository.persist(pedido);
    }

    @Override
@Transactional
public void pagamentoBoleto(Long pedidoId) {
   
    Pedido pedido = pedidoRepository.findById(pedidoId);
    if (pedido == null) {
        throw new ValidationException("Pedido", "Pedido não encontrado com o ID: " + pedidoId);
    }

    String usernameAutenticado = jwt.getName();

    if (!pedido.getCliente().getUsuario().getUsername().equals(usernameAutenticado)) {
        throw new ValidationException("Acesso Negado", "Você não tem permissão para pagar este pedido.");
    }

    if (pedido.getStatus() == StatusPedido.CANCELADO) {
        throw new ValidationException("Status", "Não é possível pagar um pedido cancelado.");
    }

    if ("Pedido pago".equals(pedido.getStatus().getDescricao())) {
        throw new ValidationException("Status", "Pedido já foi pago.");
    }

    pedido.setStatus(StatusPedido.PAGO);
    pedido.setTipoPagamento("Boleto");
    pedidoRepository.persist(pedido);
}

    
}
