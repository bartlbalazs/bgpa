import { Component, OnInit, OnDestroy } from '@angular/core';
import { ApiService } from '../api.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-stats',
  templateUrl: './user-stats.component.html',
  styleUrls: ['./user-stats.component.css']
})
export class UserStatsComponent implements OnInit, OnDestroy {

  view: any[] = [700, 400];
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'Number';
  showYAxisLabel = true;
  yAxisLabel = 'Value';
  timeline = true;
  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };
  showLabels = true;
  explodeSlices = false;
  doughnut = false;


  statSubscription: Subscription;

  userId: String = ''
  categoryPopularities: any = []
  mechanismPopularities: any = []

  constructor(public apiService: ApiService) {
    this.statSubscription = this.apiService.userStats.subscribe(message => {
      if (message.userId) {
        this.userId = message.userId
        this.categoryPopularities = message.categoryPopularities.map(c => new Object({ "name": c.entityName, "value": c.gamesCount }))
        this.mechanismPopularities = message.mechanismPopularities.map(c => new Object({ "name": c.entityName, "value": c.gamesCount }))
      }
    })
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.statSubscription.unsubscribe();
  }
}
