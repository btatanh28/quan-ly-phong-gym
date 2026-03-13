import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';

@Injectable({
  providedIn: 'root',
})
export class CheckInService {
  private apiUrl = `${API_CURRENT}/check-in`;

  constructor(private http: HttpClient) {}

  checkIn(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, data);
  }
}
