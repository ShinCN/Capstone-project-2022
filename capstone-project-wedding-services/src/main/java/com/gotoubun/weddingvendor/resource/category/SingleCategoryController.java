package com.gotoubun.weddingvendor.resource.category;

import com.gotoubun.weddingvendor.data.category.CategoryResponse;
import com.gotoubun.weddingvendor.message.MessageToUser;
import com.gotoubun.weddingvendor.service.account.AccountService;
import com.gotoubun.weddingvendor.service.category.SingleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gotoubun.weddingvendor.resource.MessageConstant.NO_RESULTS;

/**
 * The type Single category controller.
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/single-category")
public class SingleCategoryController {

    @Autowired
    private SingleCategoryService singleCategoryService;

    @Autowired
    private AccountService accountService;

    /**
     * Gets all service pack.
     *
     * @return the all service pack
     */
    @GetMapping
    public ResponseEntity<?> getAllServicePack() {
        // TODO Auto-generated method stub
        //check login

        List<CategoryResponse> singleCategoryResponses = (List<CategoryResponse>) singleCategoryService.findAll();
        if (singleCategoryResponses.size() == 0) {
            return new ResponseEntity<>(new MessageToUser(NO_RESULTS), HttpStatus.OK);
        }

        return new ResponseEntity<>(singleCategoryResponses, HttpStatus.OK);
    }

}
