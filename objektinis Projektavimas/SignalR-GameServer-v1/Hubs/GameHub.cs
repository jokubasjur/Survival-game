using Microsoft.AspNetCore.SignalR;
using System.Threading.Tasks;
using System;
using Microsoft.Extensions.Logging;
using System.Collections.Generic;
using System.Linq;
using System.Timers;
using System.Threading;

namespace SignalR_GameServer_v1.Hubs
{
    public class GameHub : Hub
    {
        private static List<string> _connectedClients = new List<string>();     // client list
        private static List<Tuple<int, string, string>> _matches                // match list - not implemented. TODO.
            = new List<Tuple<int, string, string>>();
        ILogger<GameHub> _logger;                                               // To print game logs.

        public GameHub(ILogger<GameHub> logger)
        {
            _logger = logger;
        }

        public async Task SendMessage(string user, string message)
        {
            await Clients.All.SendAsync("ReceiveMessage", user, message);
        }

        //Sends data from client (player) to server, back to client (his teammate) about player's position, etc...
        public async Task UpdateTeamMate(string user, int posX, int posY, int posXVel, int posYVel, int healthPoints, bool isReady, bool isShooting)
        {
            await Clients.Others.SendAsync("ReceiveUpdateTeamMate", user, posX, posY, posXVel, posYVel, healthPoints, isReady, isShooting);
        }

        public async Task UpdatePlayerColor(int teamColor)
        {
            await Clients.Others.SendAsync("ReceivePlayerColor", teamColor);
        }
        public async Task UpdateShootingStrategy(string strategy)
        {
            await Clients.Others.SendAsync("ReceivePlayerShootingStrategy", strategy);
        }

        public async Task UpdatePlayerBulletType(string bulletType)
        {
            await Clients.Others.SendAsync("ReceivePlayerBulletType", bulletType);
        }

        public async Task UpdateEnemySpeed(string enemyName, double enemySpeed)
        {
            await Clients.Others.SendAsync("ReceiveUpdateEnemySpeed", enemyName, enemySpeed);
        }

        public async Task UpdateEnemy(string enemyName)
        {
            await Clients.Others.SendAsync("ReceiveUpdateEnemy", enemyName);
        }

        public async Task UpdatePoints(int points)
        {
            await Clients.Others.SendAsync("ReceivePlayerPoints", points);
        }

        //Adds connected user to the list, sends clientID to game-client (player).
        public override async Task OnConnectedAsync()
        {
            await SendMessage(Context.ConnectionId, "CONNECTED");
            _connectedClients.Add(Context.ConnectionId);
            await base.OnConnectedAsync();
            _logger.LogInformation("User: " + Context.ConnectionId + " connected");
        }

        //Removes user from the connected list.
        public override async Task OnDisconnectedAsync(Exception ex)
        {
            await SendMessage(Context.ConnectionId, "DISCONNECTED");
            _connectedClients.Remove(Context.ConnectionId);
            await base.OnDisconnectedAsync(ex);
            _logger.LogInformation("User: " + Context.ConnectionId + " disconnected");
        }

        //Returns client ID.
        public string GetConnectionId()
        {
            return Context.ConnectionId;
        }






















        // LATER, TO FIX: 

        /*public override async Task OnConnectedAsync()
{
    int _matchNum;
    int temp1;
    string temp2;
    string temp3;

    if (!_connectedClients.Contains(Context.ConnectionId))
    {
        _connectedClients.Add(Context.ConnectionId);
        if (_matches.Count == 0)
        {
            _matchNum = 0;
            Tuple<int, string, string> temp = new Tuple<int, string, string>(0, Context.ConnectionId, String.Empty);
            _matches.Add(temp);
        } else
        {
            _matchNum = _matches.FindIndex(t => t.Item1 >= 0 && (t.Item2 == "" || t.Item3 == ""));
            if (_matchNum == -1)
            {
                int matchIndex = _matches.Count;
                Tuple<int, string, string> temp = new Tuple<int, string, string>(matchIndex, Context.ConnectionId, String.Empty);
                _matchNum = matchIndex;
                _matches.Add(temp);
            } else
            {
                if (_matches[_matchNum].Item2 == "")
                {
                    _matches[_matchNum] = new Tuple<int, string, string>(_matches[_matchNum].Item1, Context.ConnectionId, _matches[_matchNum].Item3);
                }
                else
                {
                    _matches[_matchNum] = new Tuple<int, string, string>(_matches[_matchNum].Item1, _matches[_matchNum].Item2, Context.ConnectionId);
                }
            }
        }
    } else
    {
        if (_matches.Count == 0)
        {
            _matchNum = 0;
            Tuple<int, string, string> temp = new Tuple<int, string, string>(0, Context.ConnectionId, String.Empty);
            _matches.Add(temp);
        }
        else
        {
            _matchNum = _matches.FindIndex(t => t.Item1 > 0 && (t.Item2 == "" || t.Item3 == ""));
            temp1 = _matches[_matchNum].Item1;
            temp2 = _matches[_matchNum].Item2;
            temp3 = _matches[_matchNum].Item3;
            if (_matches[_matchNum].Item2 == "")
            {
                _matches[_matchNum] = new Tuple<int, string, string>(temp1, Context.ConnectionId, temp3);
            }
            else
            {
                _matches[_matchNum] = new Tuple<int, string, string>(temp1, temp2, Context.ConnectionId);
            }
        }

    }

    _logger.LogInformation("User: " + Context.ConnectionId + " connected, added to: " + _matchNum + " GROUP");
    await SendMessage(Context.ConnectionId, _matchNum.ToString());
    await base.OnConnectedAsync();
}*/
    }
}
