/******************************************************
* 浙江卓锐科技股份有限公司 版权所有 © Copyright 2015
*
* 项目名称: cloud-cms-webapp
* 创建日期: 2015年12月10日 上午10:38:32
* 作者:    肖玉明   xiaoymin@foxmail.com
*******************************************************/
package com.wanglu.movcat.advice;

import com.wanglu.movcat.exception.UserNotLoginException;
import com.wanglu.movcat.model.Result;
import com.wanglu.movcat.util.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value=UserNotLoginException.class)
	@ResponseBody
	public Result handlerUserNotLoginException(HttpServletRequest request, HttpServletResponse response, UserNotLoginException ex){
		Result result = ResultUtil.error("用户未登录");
		result.setCode(401);
		return result;
	}
}

