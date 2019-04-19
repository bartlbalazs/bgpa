import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  public userStats:BehaviorSubject<any> = new BehaviorSubject<any>({});

  fetchStats(userId: string) {
    this.httpClient.get('/api/stats/' + userId).subscribe((res: any[]) => {
      this.userStats.next(res)
    });
  }
}
