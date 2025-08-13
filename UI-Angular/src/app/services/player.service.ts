import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Player } from '../../interfaces/player';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private apiUrl = 'http://localhost:9000';

  constructor(private http: HttpClient) { }

  getPlayers(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/json/players`);
  }

  getPlayersInfo(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/json/exist/playersInfo`);
  }



  // getPlayerById(playerId: number): Observable<any> {
  //   return this.http.get<any>(`${this.apiUrl}/${playerId}`);
  // }

  updatePlayer(player: Player): Observable<Player> {
    return this.http.post<Player>(`${this.apiUrl}/api/json/players/update`, player);
  }

  // getPlayerByFullName(fullName: string): Observable<any> {
  //   return this.http.get<any>(`${this.apiUrl}/${fullName}`);
  // }

  //  getContractOption(playerId: number): Observable<any> {
  //   return this.http.get<any>(`${this.apiUrl}/${playerId}/contractOption`);
  // }
    addPlayer(player: Player): Observable<any> {
    return this.http.put<Player>(`${this.apiUrl}/api/json/exist/players/add`, player);
  }

  
  //  getPlayersByCitizenshipAndFoot(citizenship: string, foot: string): Observable<any> {
  //   return this.http.get<any>(`${this.apiUrl}/citizenship/${citizenship}/foot/${foot}`);
  // }
  deletePlayer(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/json/exist/delete/players/${id}`);
  }

}
