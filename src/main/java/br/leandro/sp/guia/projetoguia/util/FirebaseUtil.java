package br.leandro.sp.guia.projetoguia.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FirebaseUtil {
	// variavel para guardar as credenciais de acesso
	private Credentials credenciais;
	// variavel para acessar e manipular o storage
	private Storage storage;
	// constante para o nome do bucket
	private final String BUCKET_NAME = "guidepub-leandro.appspot.com";
	// constante para o prefixo da URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/"+BUCKET_NAME+"/o/";
	// constante para o sufixo da URL
	private final String SUFFIX ="?alt=media";
	// constante para URL
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FirebaseUtil() {
		// acessar o arquivo JSON com a kew privada 
		Resource resource = new ClassPathResource("key_firebase.json");
		// gera uma credencial no firebase atraves da chave do arquivo
		try {
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			// cria o Storage para manipular os dados no firebase
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
			
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage()); 
		}
	}
	
	// metoda para extrair a extensão do arquivo
	private String getExtensao(String nomeArquivo) {
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
	// metodo que faz o upload
	public String upload(MultipartFile arquivo) throws IOException  {
		// gera um nome aleatorio para o arquivo
		String nomeArquivo =  UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		// criar um blobid atravez do nome gerado para o arquivo
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);
		// criar um BlobInfo atraves do BlobId
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		//gravar o BlobInfo no storage passando os bytes do arquivo
		storage.create(blobInfo, arquivo.getBytes());
		
		//retorna a URL do arquivo gerado no Storage
		return String.format(DOWNLOAD_URL, nomeArquivo);
	}
	
	// metodo para excluir o arquivo do Storage
	public void delete(String nomeArquivo) {
		// retirar  o prefixo e sufixo da String
		nomeArquivo = nomeArquivo.replace(PREFIX, "").replace(SUFFIX, "");
		// obtem um Blob atraves do nome 
		Blob blob = storage.get(BlobId.of(BUCKET_NAME, nomeArquivo));
		// deleta atraves do Blob
		storage.delete(blob.getBlobId());
	}
}
