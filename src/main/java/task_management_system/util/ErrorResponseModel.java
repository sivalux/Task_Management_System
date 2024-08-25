package task_management_system.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponseModel {

    private Date timeStamp;

    private String errorMessage;
}
