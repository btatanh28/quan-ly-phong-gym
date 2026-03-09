import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiUrl = `${API_CURRENT}/bai-viet`;

  constructor(private http: HttpClient) {}

  addPost(baiViet: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, baiViet);
  }

  getAllPost(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) =>
        (params[key] == null || params[key] === '') && delete params[key],
    );
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  getPostById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  updatePost(baiViet: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/edit/${baiViet.ID}`, baiViet);
  }

  deletePost(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
