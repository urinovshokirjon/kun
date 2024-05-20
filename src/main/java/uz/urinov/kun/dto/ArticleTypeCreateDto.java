package uz.urinov.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeCreateDto {

    @NotNull(message = " Type number bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Type number ning qiymati minimal 1 bo'lsin")
    private Integer orderNumber;

    @NotBlank(message = "Name Uz  bo'sh bo'lishi mumkin emas")
    @Size(min = 3, max = 50, message = "Berilgan Type (Name Uz) ning uzunligi 3 va 50 orasida bo'lishi kerak")
    private String nameUz;

    @NotBlank(message = "Name Ru  bo'sh bo'lishi mumkin emas")
    @Size(min = 3, max = 50, message = "Berilgan Type (Name Ru) ning uzunligi 3 va 50 orasida bo'lishi kerak")
    private String nameRu;

    @NotBlank(message = "Name En  bo'sh bo'lishi mumkin emas")
    @Size(min = 3, max = 50, message = "Berilgan Type (Name En) ning uzunligi 3 va 50 orasida bo'lishi kerak")
    private String nameEn;

}
