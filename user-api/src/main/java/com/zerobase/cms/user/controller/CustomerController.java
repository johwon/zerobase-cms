package com.zerobase.cms.user.controller;

import com.zerobase.cms.domain.config.JwtAuthenticationProvider;
import com.zerobase.cms.domain.domain.common.UserVo;
import com.zerobase.cms.user.domain.customer.ChangeBalanceForm;
import com.zerobase.cms.user.domain.customer.CustomerDto;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.exception.CustomerException;
import com.zerobase.cms.user.service.customer.CustomerBalanceService;
import com.zerobase.cms.user.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.zerobase.cms.user.exception.ErrorCode.NOT_FOUND_USER;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final CustomerBalanceService customerBalanceService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@Parameter(in = ParameterIn.HEADER, description = "Access Token", required = true)
                                                   @RequestHeader(name="X-AUTH-TOKEN") String token){
        UserVo vo = provider.getUserVo(token);
        Customer c = customerService.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()->new CustomerException(NOT_FOUND_USER)
        );
        return ResponseEntity.ok(CustomerDto.form(c));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name="X-AUTH-TOKEN") String token,
                                                 @RequestBody ChangeBalanceForm form){
        UserVo vo = provider.getUserVo(token);
        return ResponseEntity.ok(customerBalanceService.changeBalance(vo.getId(), form).getChangeMoney());
    }

}
