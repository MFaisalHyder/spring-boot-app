package com.spring.project.response;

import com.spring.project.dto.UserDTO;
import lombok.*;

import java.util.List;

/**
 * @author: S776781 - Muhammad Faisal Hyder
 * @Date: 30/10/2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class UserServiceResponse extends Response {

    private List<UserDTO> user;

}