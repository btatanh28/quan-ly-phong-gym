// package com.CuaHang.QuanLyCuaHang.core.mediator;

// import com.tecapro.app.user.service.UserService;
// import com.tecapro.core.annotation.Response;
// import com.tecapro.core.exception.ForbiddenException;
// import com.tecapro.core.model.request.IStateRequest;
// import com.tecapro.core.utility.TimeUtil;
// import com.tecapro.domain.enums.UserLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.support.AbstractApplicationContext;
// import org.springframework.core.ResolvableType;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Service;

// @Slf4j
// @Component
// @RequiredArgsConstructor(onConstructor = @__(@Autowired))
// public class Mediator implements IMediator {

//     private final AbstractApplicationContext context;

//     @Override
//     @SuppressWarnings("unchecked")
//     public ResponseEntity<Object> send(Object request) {

//         if (request instanceof IStateRequest) {
//             var userLevel = userService.getUser().getUserInfo().getUserLevel();
//             if ((userLevel >= UserLevel.PROVINCE.value && ((IStateRequest) request).getProvinceId() == null)
//                     || (userLevel >= UserLevel.DISTRICT.value && ((IStateRequest) request).getDistrictId() == null)
//                     || (userLevel.equals(UserLevel.WARD.value) && ((IStateRequest) request).getWardId() == null)) {
//                 throw new ForbiddenException("Không có quyền thực hiện yêu cầu này");
//             }
//         }

//         var requestClass = request.getClass();

//         var responseClass = requestClass.getAnnotation(Response.class).target();

//         var beanNames = context.getBeanNamesForType(
//                 ResolvableType.forClassWithGenerics(IRequestHandler.class, requestClass, responseClass)
//         );

//         var requestHandler = (IRequestHandler<Object, Object>) context.getBean(beanNames[0]);

//         return ResponseEntity.status(HttpStatus.OK).body(requestHandler.handle(request));
//     }

//     @Override
//     @SuppressWarnings("unchecked")
//     public ResponseEntity<Object> export(Object request) {

//         if (request instanceof IStateRequest) {
//             var userLevel = userService.getUser().getUserInfo().getUserLevel();
//             if ((userLevel >= UserLevel.PROVINCE.value && ((IStateRequest) request).getProvinceId() == null)
//                     || (userLevel.equals(UserLevel.WARD.value) && ((IStateRequest) request).getWardId() == null)) {
//                 throw new ForbiddenException("Không có quyền thực hiện yêu cầu này");
//             }
//         }

//         var requestClass = request.getClass();

//         var responseClass = requestClass.getAnnotation(Response.class).target();

//         var beanNames = context.getBeanNamesForType(
//                 ResolvableType.forClassWithGenerics(IRequestHandler.class, requestClass, responseClass)
//         );

//         var requestHandler = (IRequestHandler<Object, Object>) context.getBean(beanNames[0]);

//         return ResponseEntity
//                 .ok()
//                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= export_" + TimeUtil.now() + ".xlsx")
//                 .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                 .body(requestHandler.handle(request));
//     }

//     @Override
//     @SuppressWarnings("unchecked")
//     public void publish(Object message) {

//         var messageClass = message.getClass();

//         var beanNames = context.getBeanNamesForType(
//                 ResolvableType.forClassWithGenerics(IMessageHandler.class, messageClass)
//         );

//         for (String beanName : beanNames) {
//             var messageHandler = (IMessageHandler<Object>) context.getBean(beanName);

//             messageHandler.handle(message);
//         }
//     }
// }
