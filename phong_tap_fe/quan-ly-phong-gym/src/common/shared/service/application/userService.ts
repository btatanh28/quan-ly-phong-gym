import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { API_CURRENT } from './api-base';

export interface Users {
  IDUser: string;
  TenNguoiDung: string;
  Email: string;
  SoDienThoai: string;
  DiaChi: string;
  MatKhau: string;
}

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  private apiUrl = `${API_CURRENT}`;

  constructor(private http: HttpClient) {}

  getAllUsers(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) => (params[key] == null || params[key] === '') && delete params[key]
    );
    return this.http.get<any>(`${this.apiUrl}/user/list`, { params });
  }

  getUserById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/user/${id}`);
  }

  updateUser(data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/user/edit/${data.id}`, data);
  }

  deleteUser(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/user/${id}`);
  }
}
