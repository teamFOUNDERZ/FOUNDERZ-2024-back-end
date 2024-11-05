package com.founderz.common.crypto;

import com.founderz.common.vo.auth.SecuredPassword;
import com.founderz.common.vo.auth.Password;

public interface PasswordEncoder {
    String encode(Password rawPassword);
    Boolean matches(Password rawPassword, SecuredPassword hashedPassword);
}
