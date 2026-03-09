import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { API_CURRENT } from "./api-base";

export interface CoSo{
    IDCoSo: number | null;
    CoSoNhaHang: string;
}

@Injectable({
    providedIn: 'root',
})
export class CoSoService{
    private apiUrl = `${API_CURRENT}/coso`;

    constructor(private http: HttpClient){}

    addCoSo(DataCoSo: any): Observable<any>{
        return this.http.post<any>(`${this.apiUrl}`, DataCoSo);
    }

    updateCoSo(id: number, DataCoSo: any):Observable<any>{
        return this.http.put<any>(`${this.apiUrl}/${id}`,DataCoSo);
    }

    deleteCoSo(id: number): Observable<any>{
        return this.http.delete<any>(`${this.apiUrl}/${id}`);
    }

    getAllCoSo():Observable<any>{
        return this.http.get<any>(`${this.apiUrl}`);
    }
}
