//package com.gotoubun.weddingvendor.validator.vendor;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import com.gotoubun.weddingvendor.domain.user.VendorProvider;
//import com.gotoubun.weddingvendor.service.IService;
//
//@Component
//public class VendorValidator implements Validator {
//	@Autowired
//	private IService<VendorProvider> vendorService;
//
//	@Override
//	public boolean supports(Class<?> clazz) {
//		// TODO Auto-generated method stub
//		return VendorProvider.class.isAssignableFrom(clazz);
//}
//
//	@Override
//	public void validate(Object target, Errors errors) {
//		VendorProvider vendor =(VendorProvider) target;
//		List<VendorProvider> vendors =(List<VendorProvider>) vendorService.findAll();
//		for (VendorProvider vendorProvider : vendors) {
//			if(vendor.getPhone().equals(vendorProvider.getPhone())) {
//				errors.rejectValue("phone", "field.phoneExisted", "Phone is existed in DB");
//			}
//		}
//	}
//	}
