package com.utc.securityprojectb.service;

import com.utc.securityprojectb.dto.request.RegisterRequest;
import com.utc.securityprojectb.dto.response.AccountResponse;
import com.utc.securityprojectb.entity.Account;
import com.utc.securityprojectb.service.base.BaseService;

public interface AccountService extends BaseService<Account, Long> {

  AccountResponse register(RegisterRequest request);
}
