package com.share.helloconsumer.control;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.share.helloconsumer.api.HelloApi;
import com.share.helloconsumer.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="user")
@Api(value = "PageController", description = "user接口")
public class ApiController {

    @Autowired
    private HelloApi helloApi;

    @ApiOperation(value="getUser", notes="获取用户信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true ,dataType = "string")
    })
    @HystrixCommand(fallbackMethod = "defaultUser")
    @RequestMapping(value="/{userId}",method = RequestMethod.GET)
    public User getUser(@PathVariable("userId") String userId) {

//        try {                            //模拟异常
//            Thread.sleep(new Integer(10000));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return helloApi.getUser(userId);
    }

    public  User defaultUser(String userId){ //参数必须保持一致 否则报错 不能找到方法
        User user =  new User();
        user.setId(userId);
        user.setName("defaultuser");
        return user;
    }



}
