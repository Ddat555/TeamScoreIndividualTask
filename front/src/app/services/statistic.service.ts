import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SummaryStatistic,
  CategoryStatistic,
  TypePaymentStatistic,
  SellerStatistic,
  SellerStatisticInfo,
  PaymentStatisticInfo,
  CategoryStatisticInfo
 } from '../models/statistic.models';

@Injectable({
  providedIn: 'root'
})
export class StatisticService {

  private apiUrl = "http://localhost:8080/api/v1/statistic";

  constructor(private http:HttpClient) { }

  getCategoryStatistic(from: string, to: string): Observable<CategoryStatisticInfo> {
    const params = new HttpParams().set('from', from).set('to', to);
    return this.http.get<CategoryStatisticInfo>(`${this.apiUrl}/category`, { params });
  }

  getPaymentStatistic(from: string, to: string): Observable<PaymentStatisticInfo> {
    const params = new HttpParams().set('from', from).set('to', to);
    return this.http.get<PaymentStatisticInfo>(`${this.apiUrl}/type-payment`, { params });
  }

  getSellerStatistic(from: string, to: string): Observable<SellerStatisticInfo> {
    const params = new HttpParams().set('from', from).set('to', to);
    return this.http.get<SellerStatisticInfo>(`${this.apiUrl}/top-seller`, { params });
  }
}
