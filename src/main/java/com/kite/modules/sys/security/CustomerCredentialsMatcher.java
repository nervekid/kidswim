package com.kite.modules.sys.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Service;

@Service
public class CustomerCredentialsMatcher extends SimpleCredentialsMatcher{
	
	@Override  
    public boolean doCredentialsMatch(AuthenticationToken authcToken,  
            AuthenticationInfo info) {  
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;  
  
        Object tokenCredentials = encrypt(String.valueOf(token.getPassword()));  
        Object accountCredentials = getCredentials(info);  
        return equals(tokenCredentials, accountCredentials);  
       
    }  
  
    // 将传进来密码加密方法  
    private String encrypt(String data) {  
    		  MessageDigest messageDigest;
    		  String encodeStr = "";
    		// TODO Auto-generated method stub
    		  System.out.println("data====="+data);
              try {
            	  
            	  messageDigest = MessageDigest.getInstance("MD5");
                  byte[] hash = messageDigest.digest(data.getBytes("UTF-8"));
                  encodeStr =    DatatypeConverter.printHexBinary(hash);
                 
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}catch (NoSuchAlgorithmException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            
              return encodeStr.replace("-", "").toLowerCase();
    	  }
    

}
