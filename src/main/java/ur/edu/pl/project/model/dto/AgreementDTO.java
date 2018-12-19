package ur.edu.pl.project.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import ur.edu.pl.project.model.Agreement;

import lombok.Data;

@Data
public class AgreementDTO {

    private int id;
    private String number;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateFrom;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateTo;
    private boolean active;

    public AgreementDTO(Agreement agreement) {
        this.id = agreement.getId();
        this.number = agreement.getNumber();
        this.dateFrom = agreement.getDateFrom();
        this.dateTo = agreement.getDateTo();
        this.active = agreement.isActive();
    }
}