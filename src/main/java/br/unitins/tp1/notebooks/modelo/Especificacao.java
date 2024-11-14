package br.unitins.tp1.notebooks.modelo;
 
import jakarta.persistence.Entity;

@Entity
public class Especificacao extends DefaultEntity {

 
    private String processador;
    private String memoriaRam;
    private String armazenamento;
    private String tela;
    private String bateria;
    private Double peso;


    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(String memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
    }

    public String getTela() {
        return tela;
    }

    public void setTela(String tela) {
        this.tela = tela;
    }

    public String getBateria() {
        return bateria;
    }

    public void setBateria(String bateria) {
        this.bateria = bateria;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getDetalhes() {
        return String.format("Processador: %s, RAM: %s, Armazenamento: %s, Tela: %s, Bateria: %s, Peso: %.2f kg",
                processador, memoriaRam, armazenamento, tela, bateria, peso);
    }
}
