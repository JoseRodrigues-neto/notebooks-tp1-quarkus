package br.unitins.tp1.notebooks.modelo.converterjpa;

import br.unitins.tp1.notebooks.modelo.StatusPedido;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusPedidoConverter implements AttributeConverter<StatusPedido, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusPedido status) {
        return status == null ? null : status.getCodigo();  
    }

    @Override
    public StatusPedido convertToEntityAttribute(Integer codigo) {
        return StatusPedido.valueOf(codigo);  
    }
}
