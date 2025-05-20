package com.zerobase.cms.user.service.seller;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.domain.repository.SellerRepository;
import com.zerobase.cms.user.exception.CustomerException;
import com.zerobase.cms.user.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.zerobase.cms.user.exception.ErrorCode.*;
import static com.zerobase.cms.user.exception.ErrorCode.EXPIRE_CODE;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public Optional<Seller> findByIdAndEmail(Long id, String email){
        return  sellerRepository.findByIdAndEmail(id,email);
    }

    public Optional<Seller> findValidSeller(String email, String password){
        return sellerRepository.findByEmailAndPasswordAndVerifyIsTrue(email,password);
    }

    public Seller signUp(SignUpForm form){
        return sellerRepository.save(Seller.from(form));
    }

    public boolean isEmailExist(String email){
        return sellerRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code){
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(()->new CustomerException(NOT_FOUND_USER));
        if(seller.isVerify()){
            throw new CustomerException(ALREADY_VERIFY);
        }
        if(!seller.getVerificationCode().equals(code)){
            throw new CustomerException(WRONG_VERIFICATION);
        }
        if(seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomerException(EXPIRE_CODE);
        }
        seller.setName("hw");
        seller.setVerify(true);
    }

    @Transactional
    public LocalDateTime ChangeSellerValidateEmail(Long sellerId, String verificationCode){
        Optional<Seller> sellerOptional = sellerRepository.findById(sellerId);

        if(sellerOptional.isPresent()){
            Seller seller = sellerOptional.get();
            seller.setVerificationCode(verificationCode);
            seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return seller.getVerifyExpiredAt();
        }else{

            return LocalDateTime.now();
        }

    }

}
