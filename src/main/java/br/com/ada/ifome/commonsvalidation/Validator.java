package br.com.ada.ifome.commonsvalidation;

import org.springframework.stereotype.Service;

@Service
public class Validator {
    public boolean validaCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.length() == 11;
    }

    public boolean validaRG(String rg) {
        rg = rg.trim();
        return rg.length() == 7;
    }

    public boolean validaCNH(String cnh) {
        cnh = cnh.replaceAll("[^0-9]", "");
        return cnh.length() == 11;
    }

}
