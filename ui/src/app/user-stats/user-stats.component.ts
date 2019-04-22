import { Component, OnInit, OnDestroy } from '@angular/core';
import { ApiService } from '../api.service';
import { Subscription } from 'rxjs';
import { ChartConfig } from '../shared/ChartConfig'
import { PopularityData } from '../shared/PopularityData'

@Component({
  selector: 'app-user-stats',
  templateUrl: './user-stats.component.html',
  styleUrls: ['./user-stats.component.css']
})
export class UserStatsComponent implements OnInit, OnDestroy {

  chartConfig: ChartConfig

  statSubscription: Subscription;

  userId: String

  popularities: any = {}
  selected_items: any = {}

  constructor(public apiService: ApiService) { }

  ngOnInit() {
    this.statSubscription = this.apiService.userStats.subscribe(this.handleUserStats())
    this.chartConfig = new ChartConfig()
  }

  private handleUserStats(): (value: any) => void {
    return message => {
      if (message.userId) {
        this.userId = message.userId;
        this.popularities['theme'] = PopularityData.fromRawData(message.categoryPopularities);
        this.popularities['mechanism'] = PopularityData.fromRawData(message.mechanismPopularities);
        this.selected_items = {}
      }
    };
  }

  ngOnDestroy(): void {
    this.statSubscription.unsubscribe();
  }

  onSelectFromChart(chartId: string, $event: any) {
    let id = $event.name || $event
    this.selected_items[chartId] = this.popularities[chartId].fullData[id]
  }
}
