import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CURRENT } from './api-base';
@Injectable({
  providedIn: 'root',
})
export class ChatBotService {
  private apiUrl = `${API_CURRENT}/gemini`;

  constructor(private http: HttpClient) {}

  ChatBot(mess: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, mess, {
      responseType: 'text',
    });
  }
}
