import { Component } from '@angular/core';
import { SearchParams, StatisticChoiceComponent, StatisticType } from './statistic-choice/statistic-choice.component';
import { SummaryStatistic } from '../models/statistic.models';
import { StatisticService } from '../services/statistic.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SummaryComponent } from './summary/summary.component';
import { DetailedComponent } from './detailed/detailed.component';

@Component({
  selector: 'app-statistic-info',
  standalone: true,
  imports: [StatisticChoiceComponent, CommonModule, FormsModule, HttpClientModule, SummaryComponent, DetailedComponent, StatisticChoiceComponent,
    DetailedComponent, SummaryComponent
  ],
  templateUrl: './statistic-info.component.html',
  styleUrl: './statistic-info.component.css'
})
export class StatisticInfoComponent {

  currentData: {
    type: StatisticType;
    summary: SummaryStatistic | null;
    items: any[];
    title: string;
  } | null = null;

  loading = false;
  error: string | null = null;

  StatisticType = StatisticType;

  constructor(private statisticService: StatisticService){}

  private formatToLocalDateTime(dateStr: string): string {
    const date = new Date(dateStr);
    return `${date.getFullYear()}-${String(date.getMonth()+1).padStart(2,'0')}-${String(date.getDate()).padStart(2,'0')}T00:00:00`;
  }

  private getDatesFromPeriod(period: string): { from: string, to: string } {
    const now = new Date();
    let fromDate = new Date();

    switch(period) {
      case '7days': fromDate.setDate(now.getDate() - 7); break;
      case '30days': fromDate.setDate(now.getDate() - 30); break;
      case 'month': fromDate = new Date(now.getFullYear(), now.getMonth(), 1); break;
      case 'year': fromDate = new Date(now.getFullYear(), 0, 1); break;
      default: fromDate.setDate(now.getDate() - 7);
    }

    return {
      from: this.formatToLocalDateTime(fromDate.toISOString().split('T')[0]),
      to: this.formatToLocalDateTime(now.toISOString().split('T')[0])
    };
  }

  onSearch(params: SearchParams) {
    this.loading = true;
    this.error = null;
    this.currentData = null;

    const dates = params.period === 'custom' && params.startDate && params.endDate
      ? {
          from: this.formatToLocalDateTime(params.startDate),
          to: this.formatToLocalDateTime(params.endDate)
        }
      : this.getDatesFromPeriod(params.period);

    let observable;

    switch(params.type) {
      case StatisticType.Categories:
        observable = this.statisticService.getCategoryStatistic(dates.from, dates.to);
        break;
      case StatisticType.Payments:
        observable = this.statisticService.getPaymentStatistic(dates.from, dates.to);
        break;
      case StatisticType.Sellers:
        observable = this.statisticService.getSellerStatistic(dates.from, dates.to);
        break;
      default:
        observable = this.statisticService.getCategoryStatistic(dates.from, dates.to);
    }

    observable.subscribe({
      next: (data) => {
        this.currentData = {
          type: params.type,
          summary: data.summaryStatistic,
          items: data.statisticList,
          title: this.getTitle(params.type)
        };
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading statistics', err);
        this.error = 'Ошибка загрузки статистики';
        this.loading = false;
      }
    });
  }

  private getTitle(type: StatisticType): string {
    switch(type) {
      case StatisticType.Categories: return 'Статистика по категориям';
      case StatisticType.Payments: return 'Статистика по способам оплаты';
      case StatisticType.Sellers: return 'Топ продавцы';
      default: return 'Статистика';
    }
  }

}
