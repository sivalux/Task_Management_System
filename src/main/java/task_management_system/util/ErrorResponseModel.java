package task_management_system.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponseModel {

    private LocalDateTime timeStamp;

    private String errorMessage;
}
