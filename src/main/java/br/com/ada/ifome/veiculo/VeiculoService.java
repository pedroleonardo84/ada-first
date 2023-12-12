package br.com.ada.ifome.veiculo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public boolean validaDataModeloVeiculo(Integer anoModelo) {
        return anoModelo >= 2010;
    }

    public boolean validaPlacaVeiculo(String placa) {
        String regexPlaca = "^[A-Z]{3}\\d{1}[A-Z0-9]{1}\\d{2}$|^[A-Z]{3}\\d{2}[A-Z0-9]{1}\\d{1}$";
        return placa.matches(regexPlaca);
    }

    public boolean validaRenavamVeiculo(Long renavam) {

        String renavamStr = String.valueOf(renavam);

        if (renavam == null || renavamStr.length() != 11) {
            return false;
        }

        if (veiculoRepository.findByRenavam(renavam).isPresent()) {
            return false;
        }

        int[] pesos = {3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int digitoVerificador = Character.getNumericValue(renavamStr.charAt(9));

        int soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(renavamStr.charAt(i)) * pesos[i];
        }

        int resto = soma % 11;
        int resultado = (resto == 0) ? 0 : 11 - resto;

        return resultado == digitoVerificador;
    }
}

