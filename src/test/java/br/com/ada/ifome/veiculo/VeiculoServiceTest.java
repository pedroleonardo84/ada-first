package br.com.ada.ifome.veiculo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class VeiculoServiceTest {
    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidaDataModeloVeiculo() {
        assertTrue(veiculoService.validaDataModeloVeiculo(2010));
        assertFalse(veiculoService.validaDataModeloVeiculo(2009));
    }

//    @Test
//    public void testValidaPlacaVeiculo() {
//        assertTrue(veiculoService.validaPlacaVeiculo("ABC1D23"));
//        assertFalse(veiculoService.validaPlacaVeiculo("ABC1234"));
//        assertFalse(veiculoService.validaPlacaVeiculo("ABC12D34"));
//    }

    @Test
    public void testValidaRenavamVeiculo() {
        Long renavam = 12345678901L;

        // Mock do método findByRenavam retornando um Optional vazio
        when(veiculoRepository.findByRenavam(renavam)).thenReturn(Optional.empty());

        assertTrue(veiculoService.validaRenavamVeiculo(renavam));

        // Mock do método findByRenavam retornando um Optional com um valor
        when(veiculoRepository.findByRenavam(renavam)).thenReturn(Optional.of(new Veiculo()));

        assertFalse(veiculoService.validaRenavamVeiculo(renavam));
    }

}