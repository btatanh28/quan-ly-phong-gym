package com.example.QuanLyPhongGym.app.phongtap.comments.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.phongtap.comments.command.create.CreateCommentCommand;
import com.example.QuanLyPhongGym.app.phongtap.comments.command.create.CreateCommentCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.comments.command.delete.DeleteCommentCommand;
import com.example.QuanLyPhongGym.app.phongtap.comments.command.delete.DeleteCommentCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.comments.command.update.UpdateCommentCommand;
import com.example.QuanLyPhongGym.app.phongtap.comments.command.update.UpdateCommentCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.comments.query.get.GetCommentQuery;
import com.example.QuanLyPhongGym.app.phongtap.comments.query.get.GetCommentQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.comments.query.get.GetCommentQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.comments.query.getlist.GetListCommentQuery;
import com.example.QuanLyPhongGym.app.phongtap.comments.query.getlist.GetListCommentQueryHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("comments")
@RequiredArgsConstructor

public class CommentController {
    private final CreateCommentCommandHandler createCommentCommandHandler;
    private final DeleteCommentCommandHandler deleteCommentCommandHandler;
    private final UpdateCommentCommandHandler updateCommentCommandHandler;
    private final GetCommentQueryHandler getCommentQueryHandler;
    private final GetListCommentQueryHandler getListCommentQueryHandler;

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListCommentQuery request) {
        ListResponse response = getListCommentQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetCommentQueryDTO> get(@PathVariable String id) {
        GetCommentQuery request = new GetCommentQuery();
        request.setId(id);

        GetCommentQueryDTO response = getCommentQueryHandler.handle(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DataResponse> create(@RequestBody CreateCommentCommand request) {
        DataResponse response = createCommentCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<DataResponse> update(@RequestBody UpdateCommentCommand request) {
        DataResponse response = updateCommentCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DataResponse> delete(DeleteCommentCommand request) {
        DataResponse response = deleteCommentCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
