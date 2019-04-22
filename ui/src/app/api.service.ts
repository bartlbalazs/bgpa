import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  public userStats:BehaviorSubject<any> = new BehaviorSubject<any>({});

  fetchStats(userId: string) {
    if (userId) {
      this.httpClient.get('/api/stats/' + userId).subscribe((res: any[]) => {
        if (res['userId']) {
          this.userStats.next(res)
        }
      });
    }
  }
}
