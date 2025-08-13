import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../interfaces/user';
import { Contract } from '../../interfaces/contract';
import { FootballClub } from '../../interfaces/footballClub';

@Injectable({
  providedIn: 'root'
})
export class CtService {
  private apiUrl = 'http://localhost:9000';

  constructor(private http: HttpClient) { }

  getUser(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/json/exist/user/${id}`);
  }


  addContract(contract: Contract): Observable<any> {
    return this.http.post<Contract>(`${this.apiUrl}/json/exist/contracts/add`, contract);
  }

  addFootballClub(footballClub: FootballClub): Observable<any> {
    return this.http.post<FootballClub>(`${this.apiUrl}/json/exist/footballClubs/add`, footballClub);
  }

  getFootballClubs(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/json/exist/footballClubs`);
  }
  

}
