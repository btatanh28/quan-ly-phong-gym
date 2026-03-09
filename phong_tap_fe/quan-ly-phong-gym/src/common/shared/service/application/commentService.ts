import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CURRENT } from './api-base';

export interface BinhLuan {
  IDKhachHang: number;
  IDUser: number;
  IDDanhGia: number;
  NoiDung: string;
  HinhAnh: string;
}

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private apiUrl = `${API_CURRENT}/comments`;

  constructor(private http: HttpClient) {}

  getAllComment(idDanhGia: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/list`, {
      params: { idDanhGia },
    });
  }

  getCommentById(
    id: String,
    page: number = 0,
    pageSize: number = 5,
  ): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('pageSize', pageSize.toString());

    return this.http.get<any>(`${this.apiUrl}/${id}`, { params });
  }

  addComment(commentData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, commentData);
  }

  updateComment(commentData: any): Observable<any> {
    return this.http.put<any>(
      `${this.apiUrl}/edit/${commentData.id}`,
      commentData,
    );
  }

  deleteComment(commentData: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${commentData.id}`);
  }

  // getCommentsByDanhGiaId(id: number) {
  //   return this.http.get<BinhLuan[]>(
  //     `http://localhost:5000/api/comments/by-danhgia/${id}`,
  //   );
  // }
}
