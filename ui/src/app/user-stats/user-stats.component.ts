import { Component, OnInit, OnDestroy } from '@angular/core';
import { ApiService } from '../api.service';
import { Subscription } from 'rxjs';
import { ChartConfig } from '../shared/ChartConfig'
import { PopularityData } from '../shared/PopularityData'
import { Badge } from '../shared/Badge'

@Component({
  selector: 'app-user-stats',
  templateUrl: './user-stats.component.html',
  styleUrls: ['./user-stats.component.css']
})
export class UserStatsComponent implements OnInit, OnDestroy {

  chartConfig: ChartConfig

  statSubscription: Subscription;

  userId: string = null
  badges: Badge[]
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
        this.badges = message.badges;
        this.popularities['theme'] = PopularityData.fromRawData(message.categoryPopularities);
        this.popularities['mechanism'] = PopularityData.fromRawData(message.mechanismPopularities);
        this.popularities['designer'] = PopularityData.fromRawData(message.designerPopularities);
        this.popularities['artist'] = PopularityData.fromRawData(message.artistPopularities);
        this.popularities['subDomain'] = PopularityData.fromRawData(message.subDomainPopularities);
        this.popularities['family'] = PopularityData.fromRawData(message.familyPopularities);
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
