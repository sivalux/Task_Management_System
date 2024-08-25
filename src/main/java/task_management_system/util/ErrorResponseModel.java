package task_management_system.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
public class ErrorResponseModel {

    private LocalDateTime timeStamp;

    private String errorMessage;
}
