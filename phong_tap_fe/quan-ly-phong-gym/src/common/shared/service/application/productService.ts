import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = `${API_CURRENT}/san-pham`;

  constructor(private http: HttpClient) {}

  addProduct(sanPham: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, sanPham);
  }

  // getAllProduct(): Observable<any>{
  //     return this.http.get<any>(`${this.apiUrl}`);
  // }

  getAllProduct(params: any): Observable<any> {
    Object.keys(params).forEach(
      (key) =>
        (params[key] == null || params[key] === '') && delete params[key],
    );
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  getProductById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getProductByIDDanhMuc(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/danhmucSp/${id}`);
  }

  updateProduct(sanPham: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/edit/${sanPham.ID}`, sanPham);
  }

  deleteProduct(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  getCombobox(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/combobox`);
  }

  KiemTraTonKho(id: String): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/tonkho/${id}`);
  }

  // getSearchProduct(keyword: string): Observable<any>{
  //     return this.http.get<any>(`${this.apiUrl}/search?keyword=${encodeURIComponent(keyword)}`);
  // }
}
