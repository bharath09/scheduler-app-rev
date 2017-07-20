package com.revature.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.revature.utils.EmailValidationUtil;

public class EmailWrapper {

  private String fromAddress;
  private String fromName;
  private String toName;
  private String ccName;
  private List<String> toAddress;
  private List<String> ccAddress;
  private List<String> bccAddress;
  private String subject;
  private String content;
  private String templateName;
  private Map<?, ?> templateVars;
  private Long referralId;
  private String phone;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getFromAddress() {
    return fromAddress;
  }

  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }

  public String getFromName() {
    return fromName;
  }

  public void setFromName(String fromName) {
    this.fromName = fromName;
  }

  public List<String> getToAddress() {
    return toAddress;
  }

  public void setToAddress(List<String> toAddress) {
    this.toAddress = toAddress;
  }

  public List<String> getCcAddress() {
    return ccAddress;
  }

  public void setCcAddress(List<String> ccAddress) {
    this.ccAddress = ccAddress;
  }

  public List<String> getBccAddress() {
    return bccAddress;
  }

  public void setBccAddress(List<String> bccAddress) {
    this.bccAddress = bccAddress;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public Map<?, ?> getTemplateVars() {
    return templateVars;
  }

  public void setTemplateVars(Map<?, ?> templateVars) {
    this.templateVars = templateVars;
  }


  public Long getReferralId() {
    return referralId;
  }

  public void setReferralId(Long referralId) {
    this.referralId = referralId;
  }

  public String getToName() {
    return toName;
  }

  public void setToName(String toName) {
    this.toName = toName;
  }

  public String getCcName() {
    return ccName;
  }

  public void setCcName(String ccName) {
    this.ccName = ccName;
  }

  // -------------------------------------------------------- Most often use methods



  // Add into list
  public boolean addIntoToAddress(String email) {

    if (toAddress == null) {
      toAddress = new ArrayList<>();
    }

    if (!EmailValidationUtil.isValidEmail(email)) {
      return false;
    }

    return toAddress.add(email);
  }

  public boolean addIntoToCcAddress(String email) {

    if (ccAddress == null) {
      ccAddress = new ArrayList<>();
    }

    if (!EmailValidationUtil.isValidEmail(email)) {
      return false;
    }
    return ccAddress.add(email);
  }

  public boolean addIntoBccAddress(String email) {

    if (bccAddress == null) {
      bccAddress = new ArrayList<>();
    }

    if (!EmailValidationUtil.isValidEmail(email)) {
      return false;
    }
    return bccAddress.add(email);
  }

  // Remove from list
  public boolean removeFromToAddress(String item) {
    return removeFromList(toAddress, item);
  }

  public boolean removeFromCcAddress(String item) {
    return removeFromList(ccAddress, item);
  }

  public boolean removeFromBccAddress(String item) {
    return removeFromList(bccAddress, item);
  }

  // Remove all from list
  public void removeAllFromToAddress() {
    toAddress = removeAllFromList();
  }

  public void removeAllFromCcAddress() {
    ccAddress = removeAllFromList();
  }

  public void removeAllFromBccAddress() {
    bccAddress = removeAllFromList();
  }


  private static boolean removeFromList(List<String> list, String email) {
    if (list == null) {
      return false;
    }

    return list.remove(email);
  }

  private static List<String> removeAllFromList() {
    return new ArrayList<>();
  }

}
