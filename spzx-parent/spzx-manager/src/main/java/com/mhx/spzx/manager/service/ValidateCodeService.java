package com.mhx.spzx.manager.service;

import com.mhx.spxz.model.vo.system.ValidateCodeVo;
import org.springframework.stereotype.Service;

public interface ValidateCodeService {
    ValidateCodeVo generateValidateCode();
}
