import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../../../common/shared/service/application/postService';
import { API_CURRENT } from '../../../common/shared/service/application/api-base';
import { DateTimeFormatPipe } from '../../../common/base/pipe/dateTimeFormat/dateTimeForm.component';

@Component({
  selector: 'app-knowledge',
  standalone: true, // Khuyến nghị dùng standalone
  imports: [CommonModule, DateTimeFormatPipe],
  templateUrl: './knowledge.component.html',
  styleUrls: ['./knowledge.component.css'],
})
export class KnowledgeComponent implements OnInit {
  public knowledge: any; // Đổi từ listOfData thành object đơn
  public baseUrl = API_CURRENT;

  // Tương tác
  likeCount = 120;
  isLiked = false;

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.loadPost(id);
      }
    });
  }

  private loadPost(id: string) {
    this.postService.getPostById(id).subscribe({
      next: (response) => {
        this.knowledge = response;
      },
      error: (err) => {
        console.error('❌ Lỗi khi tải bài viết:', err);
      },
    });
  }

  toggleLike() {
    this.isLiked = !this.isLiked;
    this.likeCount += this.isLiked ? 1: 1;
  }
}
