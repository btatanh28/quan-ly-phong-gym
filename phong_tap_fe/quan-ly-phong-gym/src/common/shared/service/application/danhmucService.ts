import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';

export interface DanhMuc {
  IDDanhMuc: number | null;
  TenDanhMuc: string;
}

@Injectable({
  providedIn: 'root',
})
export class DanhMucService {
  private apiUrl = `${API_CURRENT}/danh-muc-san-pham`;

  constructor(private http: HttpClient) {}

  getAllDanhMuc(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) =>
        (params[key] == null || params[key] === '') && delete params[key],
    );
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  getDanhMucById(id: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  addDanhMuc(danhMuc: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, danhMuc);
  }

  updateDanhMuc(danhMuc: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/edit/${danhMuc.id}`, danhMuc);
  }

  deleteDanhMuc(danhMuc: any): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${danhMuc.id}`);
  }

  getCombobox(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/combobox`);
  }
}
