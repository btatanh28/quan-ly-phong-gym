import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BinhLuan } from './commentService';
import { API_CURRENT } from './api-base';

export interface DanhGia {
  IDKhachHang: number;
  ID: number;
  DanhGia: number;
  BinhLuan: string;
  HinhAnh: string;
  comments?: BinhLuan[];
}

@Injectable({
  providedIn: 'root',
})
export class ForumService {
  private apiUrl = `${API_CURRENT}/danh-gia`;

  constructor(private http: HttpClient) {}

  addForum(forum: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, forum);
  }

  getAllForum(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) =>
        (params[key] == null || params[key] === '') && delete params[key],
    );
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  getForumById(id: String): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getForumByIdKhachHang(idKhachHang: String): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/khach-hang/${idKhachHang}`);
  }

  updateForum(forum: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/edit/${forum.id}`, forum);
  }

  deleteForum(forum: any): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${forum.id}`);
  }
}
