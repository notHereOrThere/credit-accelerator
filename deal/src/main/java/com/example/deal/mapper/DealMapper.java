package com.example.deal.mapper;

import com.example.credit.application.model.CreditDTO;
import com.example.credit.application.model.EmploymentDTO;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import com.example.deal.entity.Client;
import com.example.deal.entity.Credit;
import com.example.deal.entity.enums.EmploymentStatus;
import com.example.deal.entity.enums.Position;
import com.example.deal.entity.inner.Employment;
import com.example.deal.entity.inner.LoanOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DealMapper {

    @Mapping(target = "passport.passportSer", source = "passportSeries")
    @Mapping(target = "passport.passportNum", source = "passportNumber")
    @SuppressWarnings({"unchecked", "deprecation"})
    Client loanApplicationRequestDtoToClientEntity(LoanApplicationRequestDTO loanApplicationRequestDTO);

    LoanOffer loanOfferDtoToLoanOfferEntity(LoanOfferDTO loanOfferDTO);

    Credit creditDtoToCreditEntity(CreditDTO creditDTO);

    @Mapping(target = "position", source = "position", qualifiedByName = "mapPosition")
    @Mapping(target = "employmentStatus", source = "employmentStatus", qualifiedByName = "mapEmploymentStatus")
    Employment employmentDtoToEmploymentEntity(EmploymentDTO employmentDTO);


    @Named("mapPosition")
    default Position mapPosition(EmploymentDTO.PositionEnum positionEnum) {
        switch (positionEnum) {
            case OWNER:  return Position.OWNER;
            case MIDDLE_MANAGER:  return Position.MIDDLE_MANAGER;
            case TOP_MANAGER:  return Position.TOP_MANAGER;
            default: return Position.WORKER;
        }
    }

    @Named("mapEmploymentStatus")
    default EmploymentStatus mapEmploymentStatus(EmploymentDTO.EmploymentStatusEnum employmentStatusEnum) {

        switch (employmentStatusEnum) {
            case UNEMPLOYED:  return EmploymentStatus.UNEMPLOYED;
            case SELF_EMPLOYED:  return EmploymentStatus.SELF_EMPLOYED;
            case BUSINESS_OWNER:  return EmploymentStatus.BUSINESS_OWNER;
            default: return EmploymentStatus.EMPLOYED;
        }
    }
}
