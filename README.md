🐟 Fish Landing Center Database Management System

A JavaFX + MySQL system for managing community fish landings, catches, sales, and reports.

* Overview

The Fish Landing Center DBMS is a desktop application designed for local community fish landing centers.
It helps digitize and automate processes such as:

Fisherfolk registration

Recording daily fish landings

Tracking sales and revenue

Monitoring docking logs

Generating reports and analytics

Species classification and distribution tracking

This project aims to replace manual logs and Excel spreadsheets with a centralized, efficient, and user-friendly system.

-- Features
1. Dashboard

Total fish purchased

Total fisherfolk

Total revenue

Species distribution charts

Weekly/monthly landing trends

KPI cards

Quick actions shortcuts

2. Fisherfolk Management

Register / update fisherfolk

Search and filters

View purchases & sales history

3. Purchase Management

Record new purchase: species, quantity, price, date & time

Auto-compute total value

Edit and correct entries

View history

4. Sales Module

Select catch to sell

Auto-check remaining qty (via SQL view)

Payment method (Cash, Credit, Transfer)

Payment status (Paid, Partial, Unpaid)

Track unpaid balances

Revenue summaries

5. Docking Logs

Arrival & departure tracking

Daily docking history

Notes/remarks per docking

6. Reports & Analytics

Sales report (daily/weekly/monthly)

Top 5 contributors

Species distribution

Purchase volumes

Profitability Summary

Export to Png, Excel

Charts (Pie, Bar, Line, StackedBar)

7. Species Classification

Add, edit, delete species

Description & classification

Species-based summaries

8. Data Information

Backup database

Export data

Restore data

9. User Accounts

Admin / Staff roles

Update profile

Change password

Recovery key support

🗄 Database Schema (ERD)

Main Entities:

fisherfolk

species

purchase

transactions

docking_logs

users

<img width="1886" height="1002" alt="image" src="https://github.com/user-attachments/assets/9d60772a-e250-45ff-b3fc-e5e39b81dd63" />
