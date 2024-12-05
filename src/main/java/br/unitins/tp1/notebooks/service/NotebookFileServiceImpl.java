package br.unitins.tp1.notebooks.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.unitins.tp1.notebooks.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named("notebookFileService")
@ApplicationScoped
public class NotebookFileServiceImpl implements FileService {

    private final String PATH_NOTEBOOK = "C:" + File.separator +
            "Users" +
            File.separator +
            "josé neto" +
            File.separator +
            "Desktop" +
            File.separator +
            "IMAGEM-notebooks" +
            File.separator;

    private static final List<String> SUPPORTED_MIME_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png",
            "image/gif");

    private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // 10 mb

    @Override
    public String save(String nomeArquivo, byte[] arquivo) throws IOException {

        if (arquivo == null || arquivo.length == 0) {
            throw new ValidationException("Imagem", "O campo imagem está vazio.");
        }

        if (arquivo.length > MAX_FILE_SIZE) {
            throw new ValidationException("Arquivo", "O arquivo excede o tamanho máximo permitido de 10MB.");
        }

        Path diretorio = Paths.get(PATH_NOTEBOOK);
        Files.createDirectories(diretorio);

        String mimeType = Files.probeContentType(Paths.get(nomeArquivo)); // Pode retornar nulo!
        if (mimeType == null || !SUPPORTED_MIME_TYPES.contains(mimeType)) {
            throw new ValidationException("Arquivo", "Formato de arquivo não suportado ou inválido.");
        }

        String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf('.') + 1).toLowerCase();
        String novoNomeArquivo = UUID.randomUUID() + "." + extensao;
        Path filePath = diretorio.resolve(novoNomeArquivo);
        // String mimeType = Files.probeContentType(Paths.get(nomeArquivo));
        // String extensao = mimeType.substring(mimeType.lastIndexOf("/")+1);
        // String novoNomeArquivo = UUID.randomUUID() + "." + extensao;

        if (Files.exists(filePath)) {
            throw new ValidationException("Arquivo", "Nome do arquivo gerado já existe.");
        }
        // Path filePath = diretorio.resolve(novoNomeArquivo);

        if (filePath.toFile().exists()) {
            throw new ValidationException("Arquivo", "O nome do arquivo (gerado) ja existe: ");
        }

        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(arquivo);
        }

        return novoNomeArquivo;
    }

    @Override
    public File find(String nomeArquivo) {
        Path filePath = Paths.get(PATH_NOTEBOOK + nomeArquivo);
        if (!Files.exists(filePath)) {
            throw new ValidationException("NomeImagem: ", "arquivo não encontrado");
        }
        File file = new File(PATH_NOTEBOOK + nomeArquivo);
        return file;
    }

}
