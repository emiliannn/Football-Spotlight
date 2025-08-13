import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Manager } from '../../interfaces/manager';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  private apiUrl = 'http://localhost:9000';

  constructor(private http: HttpClient) { }

  getManagers(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/json/exist/managers`);
  }

  updateManager(manager: Manager): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/exist/update/manager/`, manager);
  }

  addManager(manager: Manager): Observable<any> {
    return this.http.post<Manager>(`${this.apiUrl}/json/exist/managers/add`, manager);
  }


  deleteManager(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/json/exist/delete/managers/${id}`);
  }
}
