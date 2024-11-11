package tecnologia.sea.clientapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tecnologia.sea.clientapi.domain.client.ViaCepResponse;

import org.springframework.web.client.RestClientException;

@Service
public class ViaCepService {

  private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

  @Autowired
  private RestTemplate restTemplate;

  public ViaCepResponse consultaCep(String cep) {
    try {
      return restTemplate.getForObject(VIACEP_URL, ViaCepResponse.class, cep);
    } catch (RestClientException e) {
      throw new RuntimeException("Erro ao consultar o CEP: " + cep, e);
    }
  }
}
