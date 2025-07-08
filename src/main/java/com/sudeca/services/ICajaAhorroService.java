package com.sudeca.services;

import com.sudeca.dto.CajaAhorroDTO;
import com.sudeca.model.CajaAhorro;

public interface ICajaAhorroService {
    CajaAhorro saveCajaAhorro(CajaAhorroDTO request);
    CajaAhorro updateCajaAhorro(CajaAhorroDTO request);
    CajaAhorro findByCodigoCaho(String valor);

    CajaAhorro findByRif(String valor);
}
