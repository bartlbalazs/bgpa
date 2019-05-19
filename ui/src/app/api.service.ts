import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  public userStats: BehaviorSubject<any> = new BehaviorSubject<any>({});

  public busy: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  fetchStats(userId: string) {
    if (userId) {
      this.busy.next(true)
      this.httpClient.get('/api/stats/' + userId).subscribe(
        (res: any[]) => {
          this.busy.next(false)
          if (res['userId']) {
            this.userStats.next(res)
          }
        },
        error => this.busy.next(false));
    }
  }
}
