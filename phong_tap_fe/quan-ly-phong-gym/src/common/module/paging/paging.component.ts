import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'app-paging',
  imports: [CommonModule],
  templateUrl: './paging.component.html',
  styleUrls: ['./paging.component.css'],
})
export class PagingComponent implements OnInit, OnChanges {
  @Input() totalRecords = 0; // tổng bản ghi từ DB
  @Input() pageSize = 5; // phần tử 1 trang (mặc định 5)
  @Input() currentPage = 1; // 1-based
  @Input() maxPagesToShow = 7; // tuỳ chọn: số nút trang hiển thị
  @Output() pageChange = new EventEmitter<number>(); // emit page mới (1-based)

  totalPages = 0;
  pages: number[] = [];

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges) {
    this.totalPages = Math.max(1, Math.ceil(this.totalRecords / this.pageSize));
    // ensure currentPage within range
    if (this.currentPage < 1) this.currentPage = 1;
    if (this.currentPage > this.totalPages) this.currentPage = this.totalPages;
    this.generatePages();
  }

  private generatePages() {
    const total = this.totalPages;
    const maxShow = Math.max(5, this.maxPagesToShow); // min 5
    let start = Math.max(1, this.currentPage - Math.floor(maxShow / 2));
    let end = start + maxShow - 1;

    if (end > total) {
      end = total;
      start = Math.max(1, end - maxShow + 1);
    }

    this.pages = [];
    for (let i = start; i <= end; i++) this.pages.push(i);
  }

  goTo(page: number) {
    if (page < 1) page = 1;
    if (page > this.totalPages) page = this.totalPages;
    if (page === this.currentPage) return;
    this.currentPage = page;
    this.generatePages();
    this.pageChange.emit(this.currentPage);
  }

  prev() {
    this.goTo(this.currentPage - 1);
  }
  next() {
    this.goTo(this.currentPage + 1);
  }
}
