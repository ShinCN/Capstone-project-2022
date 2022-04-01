package com.gotoubun.weddingvendor.message;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageToUser {
  private String message;
//  public MessageToUser(ConstantUtils constantUtils) {
//	this.message=constantUtils.getMessage();
//}
}
